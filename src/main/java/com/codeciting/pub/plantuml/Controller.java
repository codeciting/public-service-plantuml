package com.codeciting.pub.plantuml;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class Controller {

    private FileFormatOption option = new FileFormatOption(FileFormat.SVG);

    @GetMapping
    public void render(@RequestParam String source, HttpServletResponse response) {
        source = new String(Base64Utils.decodeFromString(source));
        SourceStringReader reader = new SourceStringReader(source);
        try {
            response.setContentType("image/svg");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "max-age=3600");
            reader.outputImage(response.getOutputStream(), option).getDescription();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
