package itmo.programming.services;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AreaCheckServiceTest {
    private final AreaCheckService service = new AreaCheckService();

    @ParameterizedTest(name = "[{index}] x={0}, y={1}, r={2} -> {3}")
    @DisplayName("Проверка попадания точки в заданную область")
    @CsvSource({
            // 2 четверть - четверть круга (x^2 + y^2 <= R^2)
            " 0.0,  1.0,  1.0,  true",  // на границе (верх)
            "-1.0,  0.0,  1.0,  true",  // на границе (лево)
            "-0.5,  0.5,  1.0,  true",  // внутри
            "-1.0,  1.0,  1.0,  false", // снаружи (угол квадрата)

            // 3 четверть - прямоугольник (x >= -R, y >= -R/2)
            "-1.0, -0.5,  1.0,  true",  // а углу
            "-0.5, -0.2,  1.0,  true",  // внутри
            "-1.1, -0.2,  1.0,  false", // вылет по X
            "-0.5, -0.6,  1.0,  false", // вылет по Y

            // 4 четверть - треугольник (y >= x - R)
            " 1.0,  0.0,  1.0,  true",  // на границе (право)
            " 0.5, -0.2,  1.0,  true",  // внутри
            " 0.5, -0.5,  1.0,  true",  // на гипотенузе
            " 0.8, -0.8,  1.0,  false", // снаружи

            // 1-я четверть - пустая
            " 0.1,  0.1,  1.0,  false",

            // дополнительные случаи
            " 0.0,  0.0,  1.0,  true",  // центр координат
            " 0.0,  0.0,  5.0,  true"   // центр с другим R
    })
    void testCheckPoint(String x, String y, String r, boolean expected) {
        BigDecimal bX = new BigDecimal(x);
        BigDecimal bY = new BigDecimal(y);
        BigDecimal bR = new BigDecimal(r);

        boolean actual = service.check(bX, bY, bR);

        assertEquals(expected, actual,
                String.format("Точка (%s, %s) при R=%s должна быть %s", x, y, r, expected));
    }
}
