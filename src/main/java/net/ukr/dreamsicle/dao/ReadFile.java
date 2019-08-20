package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;

import java.io.File;
import java.util.List;

public interface ReadFile {
    List<Currency> readFile(File file);
}
