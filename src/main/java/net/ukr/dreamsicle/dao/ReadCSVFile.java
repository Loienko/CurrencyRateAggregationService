package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReadCSVFile implements ReadFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCSVFile.class);

    @Override
    public List<Currency> readFile(File file) {
        List<Currency> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\;");
                List arrayList = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    arrayList.add(split[i].split("=")[1] + " ");
                }
                list.add(new Currency(
                        file.getName().split("\\.")[0],
                        arrayList.get(0).toString(),
                        arrayList.get(1).toString(),
                        arrayList.get(2).toString().split("\"")[0]
                ));
            }
        } catch (IOException ex) {
            LOGGER.info("Error", ex);
        }
        return list;
    }
}
