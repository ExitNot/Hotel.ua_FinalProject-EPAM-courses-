package util;

import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;

public class UtilTest {

    @Test
    void transformDateTest() {
        String input = "02/15/1991";
        String expected = "1991-02-15";

        Assertions.assertEquals(expected, Util.transformDate(input));
    }

    @Test
    void getTodayTest() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Assertions.assertEquals(new Date(c.getTimeInMillis()), Util.getToday());
    }

    @Test
    void calcPriceTest() {
        Assertions.assertEquals(1000.0, Util.calcPrice(1, 0, RoomType.RoomClass.Standard));
        Assertions.assertEquals(800.0, Util.calcPrice(1, 1, RoomType.RoomClass.Standard));
        Assertions.assertEquals(1500.0, Util.calcPrice(2, 0, RoomType.RoomClass.Deluxe));
        Assertions.assertEquals(3000.0, Util.calcPrice(4, 0, RoomType.RoomClass.Deluxe));
    }
}
