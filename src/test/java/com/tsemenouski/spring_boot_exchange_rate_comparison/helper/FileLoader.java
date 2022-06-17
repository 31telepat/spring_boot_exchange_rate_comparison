package com.tsemenouski.spring_boot_exchange_rate_comparison.helper;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

public class FileLoader {
    public static String read(String filePath) {
        try {
            var file = ResourceUtils.getFile(filePath);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
