import unittest
from isTriangle import Triangle

class DecisionCoverageTest(unittest.TestCase):
    
    def test_1(self):
        result = Triangle.classify(5, 5, 5)
        assert result == Triangle.Type.EQUILATERAL

    def test_2(self):
        result = Triangle.classify(3, 4, 5)
        assert result == Triangle.Type.SCALENE

    def test_3(self):
        result = Triangle.classify(5, 5, 7)
        assert result == Triangle.Type.ISOSCELES

    def test_4(self):
        result = Triangle.classify(5, 7, 5)
        assert result == Triangle.Type.ISOSCELES

    def test_5(self):
        result = Triangle.classify(7, 5, 5)
        assert result == Triangle.Type.ISOSCELES

    def test_6(self):
        result = Triangle.classify(-1, 5, 5)
        assert result == Triangle.Type.INVALID

    def test_7(self):
        result = Triangle.classify(1, 1, 2)
        assert result == Triangle.Type.INVALID

    def test_8(self):
        result = Triangle.classify(1, 4, 2)
        assert result == Triangle.Type.INVALID

if __name__ == '__main__':
    unittest.main()
