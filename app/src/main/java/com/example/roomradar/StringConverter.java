package com.example.roomradar;
import java.util.ArrayList;
import java.util.List;

public class StringConverter {
    public static List<Integer> convertStringToList(String input) {
        // Loại bỏ dấu "[" và "]" nếu có
        input = input.replaceAll("[\\[\\]]", "");

        // Tách chuỗi thành các phần tử bằng dấu phẩy (", ")
        String[] elements = input.split(", ");

        // Tạo ArrayList và chuyển đổi các phần tử thành số nguyên
        List<Integer> resultList = new ArrayList<>();
        for (String element : elements) {
            resultList.add(Integer.parseInt(element));
        }

        return resultList;
    }
}
