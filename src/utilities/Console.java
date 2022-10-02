/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 *
 * @author tacti
 */
public class Console {

    public static void WriteLine(final String string) {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.

        System.out.printf("[%02d:%02d:%02d.%03d] [Console] %s\n", hour, minute, second, millis, string);
    }

}
