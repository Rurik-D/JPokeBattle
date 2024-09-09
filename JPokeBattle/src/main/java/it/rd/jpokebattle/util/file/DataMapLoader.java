package it.rd.jpokebattle.util.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DataMapLoader extends FileManager{
    private static Gson gson = new Gson();
    private static HashMap<String, HashMap<String, String>> typeEffMap = loadMap("json.type");

    public static HashMap<String, HashMap<String, String>> getTypeEffMap() {
        return typeEffMap;
    }


    /**
     *
     */
    public static <T> HashMap<String, T> loadMap(String serSrcName, Class<T> tClass) {
        HashMap<String, T> map = new HashMap<>();
        String path = getAbsPath(serSrcName);

        try (FileReader reader = new FileReader(path)){
            Type dataType = TypeToken.getParameterized(HashMap.class, String.class, tClass).getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


    /**
     *
     */
    private static HashMap<String, HashMap<String, String>> loadMap(String serSrcName) {
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        String path = getAbsPath(serSrcName);

        try (FileReader reader = new FileReader(path)){
            Type dataType = new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static Map<String, Map<String, Integer[]>> loadSpawnMap() {
        Map<String, Map<String, Integer[]>> map = new HashMap<>();
        String path = getAbsPath("json.spawn");

        try (FileReader reader = new FileReader(path)) {
            Type dataType = new TypeToken<Map<String, Map<String, Integer[]>>>() {}.getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
