package com.hordiienko.onlinestore.service.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlReader {
    public static String readWelcomePage() throws IOException {
        Path path = Paths.get("src/main/resources/templates/welcome_page.html");
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }
}
