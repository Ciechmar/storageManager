package com.sda.javagda40.ciechmar.storageManager.model;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtil {
    // Klasa w któej sa funkcjonalności dla klienta
    //-wyszukanie wolnych magazynów - podgląd specyfikacji magazynu
    //-możiwośc rezerwacji

    public static Set<Storage> showfreemagazynlistBySize() {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        Set<Storage> freeStorageSet = storageList.stream().filter(storage -> storage.getStatus().equals(StorageStatus.FREE)).collect(Collectors.toSet());
        freeStorageSet.stream().sorted(Comparator.comparing(Storage::getSize)).forEach(System.out::println);
        return freeStorageSet;
    }

}
