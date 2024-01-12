import unittest
from isTriangle import Triangle

class MutationAdequateTest(unittest.TestCase):
    
    def test_1(self):
        result = Triangle.classify(10, 10, 10)
        assert result == Triangle.Type.EQUILATERAL

    def test_2(self):
        result = Triangle.classify(10, 5, 10)
        assert result == Triangle.Type.ISOSCELES

    def test_3(self):
        result = Triangle.classify(5, 10, 10)
        assert result == Triangle.Type.ISOSCELES

    def test_4(self):
        result = Triangle.classify(10, 5, 9)
        assert result == Triangle.Type.SCALENE

    def test_5(self):
        result = Triangle.classify(0, 10, 10)
        assert result == Triangle.Type.INVALID

    def test_6(self):
        result = Triangle.classify(1, 2, 3)
        assert result == Triangle.Type.INVALID

    def test_7(self):
        result = Triangle.classify(1, 1, 1)
        assert result == Triangle.Type.EQUILATERAL

    def test_8(self):
        result = Triangle.classify(10, 5, 5)
        assert result == Triangle.Type.INVALID

    def test_9(self):
        result = Triangle.classify(2, 3, 10)
        assert result == Triangle.Type.INVALID

    def test_10(self):
        result = Triangle.classify(10, 0, 10)
        assert result == Triangle.Type.INVALID

    def test_11(self):
        result = Triangle.classify(10, 10, 0)
        assert result == Triangle.Type.INVALID

    def test_12(self):
        result = Triangle.classify(3.5, 3.5, 5.5)
        assert result == Triangle.Type.ISOSCELES

    def test_13(self):
        result = Triangle.classify(2, 3, 2)
        assert result == Triangle.Type.ISOSCELES

    def test_14(self):
        result = Triangle.classify(3, 1, 2)
        assert result == Triangle.Type.INVALID

    def test_15(self):
        result = Triangle.classify(5.1, 5.1, 10.2)
        assert result == Triangle.Type.INVALID

    def test_16(self):
        result = Triangle.Type.EQUILATERAL.value
        assert result == 2

    def test_17(self):
        result = Triangle.Type.SCALENE.value
        assert result == 1

    def test_18(self):
        result = Triangle.Type.INVALID.value
        assert result == 0

    def test_19(self):
        result = Triangle.Type.ISOSCELES.value
        assert result == 3

    def test_20(self):
        result = Triangle.classify(1, 3, 2)
        assert result == Triangle.Type.INVALID
    
    def test_21(self):
        result = Triangle.classify(1, 2, 1)
        assert result == Triangle.Type.INVALID

if __name__ == "__main__":
    unittest.main()
