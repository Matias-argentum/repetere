package com.mat.repetere.service.deck;

import com.mat.repetere.dto.deck.ParsedCsvLineDto;
import com.mat.repetere.exception.MalformattedCsvFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeckCsvParser {

    public List<ParsedCsvLineDto> parse(MultipartFile csvFile) throws IOException {

        String csvContent = csvToString(csvFile);

        return parseString(csvContent);
    }

    private String csvToString(MultipartFile csvFile) throws IOException {

        return new String(csvFile.getBytes(), StandardCharsets.UTF_8).replace("\uFEFF", "");
    }

    private List<ParsedCsvLineDto> parseString(String csvContent) throws IOException {
        List<ParsedCsvLineDto> parsedList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new StringReader(csvContent));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            count++;
            if (count > 25){
                break;
            }
            if (line.isBlank()){
                continue;
            }
            String[] values = line.split(";");
            if (values.length != 5 || values[3].length() > 140 || values[4].length() > 140) {
                throw new MalformattedCsvFileException("Format error in CSV file in line: " + count);
            }
            parsedList.add(new ParsedCsvLineDto(values[0], values[1], values[2], values[3], values[4]));
        }
        br.close();
        return parsedList;
    }
}
