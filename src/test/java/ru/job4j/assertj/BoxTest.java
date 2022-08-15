package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere")
                .contains("he")
                .endsWith("ere")
                .isNotEmpty()
                .matches("Sph[e]r[e]");
    }

    @Test
    void isThisCube() {
        Box box = new Box(6, 12);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube")
                .doesNotContain("Tetrahedron")
                .startsWithIgnoringCase("cu")
                .matches(".*");
    }

    @Test
    void isThisUnknown() {
        Box box = new Box(3, 5);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object")
                .endsWith("object")
                .isNotEmpty()
                .startsWith("Un")
                .containsIgnoringCase("Unknown");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 4);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .isNotBlank();
    }

    @Test
    void isThisZero() {
        Box box = new Box(0, 10);
        assertThat(box.getNumberOfVertices()).isEven()
                .isEqualTo(0)
                .isGreaterThan(-1)
                .isLessThan(2);
    }

    @Test
    void isThisThree() {
        Box box = new Box(3, 5);
        assertThat(box.getNumberOfVertices()).isOdd()
                .isEqualTo(-1)
                .isNotZero()
                .isNegative();
    }

    @Test
    void isThisExist() {
        Box box = new Box(0, 10);
        assertThat(box.isExist()).isTrue()
                .isNotEqualTo(false);
    }

    @Test
    void isThisNotExist() {
        Box box = new Box(3, 5);
        assertThat(box.isExist()).isFalse()
                .isEqualTo(false);
    }

    @Test
    void isThisSphereGetArea() {
        Box box = new Box(0, 10);
        assertThat(box.getArea()).isEqualTo(1256d, withPrecision(1d))
                .isGreaterThan(1256d)
                .isLessThan(1257d)
                .isCloseTo(1256d, withPercentage(0.1d));
    }

    @Test
    void isThisTetrahedronGetArea() {
        Box box = new Box(4, 4);
        assertThat(box.getArea()).isCloseTo(27.7d, withPercentage(1d))
                .isGreaterThan(27.7d)
                .isLessThan(28d);
    }
}