package services;

import entities.Employee;
import entities.Moto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MotoService {
    private static MotoService instance;
    private Map<Integer, Moto> motos = new HashMap<>();

    public static MotoService getInstance() {
        if (instance == null) {
            instance = new MotoService();
        }
        return instance;
    }

    public Moto addMoto (Moto moto) {
        int id = motos.size()+1;
        moto.setId(id);
        motos.put(id, moto);
        return moto;
    }

    public Moto getMoto(int id) {
        return motos.get(id);
    }

    public Set<Moto> getAllMotos() {
        return new HashSet<>(motos.values());
    }
/*
    public Moto updateMoto(Moto moto) {
        int id = moto.getId();
        if (motos.containsKey(id)) {
            motos.put(id, moto);
            return moto;
        }
        return null;
    }

    public boolean deleteMoto(int id) {
        return motos.remove(id) != null;
    }

 */
}