package com.salesianostriana.dam.miarma.services.impl;

import com.salesianostriana.dam.miarma.config.StorageProperties;
import com.salesianostriana.dam.miarma.exception.FileNotFoundException;
import com.salesianostriana.dam.miarma.exception.StorageException;
import com.salesianostriana.dam.miarma.services.StorageService;
import com.salesianostriana.dam.miarma.utils.MediaTypeUrlResource;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public static BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) throws Exception {
        return Scalr.resize(originalImage, targetWidth);
    }


    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String storeAvatar(MultipartFile file) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+ extension,"");

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        BufferedImage bufferedImageEscaled = simpleResizeImage(bufferedImage , 500);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bufferedImageEscaled, extension, baos);

        MultipartFile newImage = new MockMultipartFile(name, baos.toByteArray());

        try {

            if (newImage.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);
                filename = name + "_" + suffix + "." + extension;
            }
            try (InputStream inputStream = newImage.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }

        return filename;

    }

    @Override
    public String storePost(MultipartFile file) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+ extension,"");

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        BufferedImage bufferedImageEscaled = simpleResizeImage(bufferedImage , 1080);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bufferedImageEscaled, extension, baos);

        MultipartFile newImage = new MockMultipartFile(name, baos.toByteArray());

        try {

            if (newImage.isEmpty())
                throw new StorageException("El fichero subido está vacío");


            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);
                filename = name + "_" + suffix + "." + extension;
            }
            try (InputStream inputStream = newImage.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }

        return filename;

    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            // Si el fichero está vacío, excepción al canto
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            newFilename = filename;
            while(Files.exists(rootLocation.resolve(newFilename))) {
                // Tratamos de generar uno nuevo
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("."+extension,"");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                newFilename = name + "_" + suffix + "." + extension;

            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }



        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);
        }

        return newFilename;

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Error al leer los ficheros almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        // Pendiente
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}