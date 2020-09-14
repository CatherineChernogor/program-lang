#include <iostream>
#include <string>

int main()
{
	double num1, num2;
	char sign;
	std::cout<< "Enter first number, sign (+,-,*,/) and second number\n";
	std::cin >> num1 >> sign >> num2;
	std::cout<< "Result is";
	/*
	if (sign == '+') std::cout << num1 + num2;
	if (sign == '-') std::cout << num1 - num2;
	if (sign == '*') std::cout << num1 * num2;
	if (sign == '/') std::cout << num1 / num2;
	*/

	switch (sign)
	{
	case '+':
		std::cout << num1 + num2;
		break;
	case '-':
		std::cout << num1 - num2;
		break;
	case '*':
		std::cout << num1 * num2;
		break;
	case '/':
		std::cout << num1 / num2;
		break;
	default:
		std::cout << "error";
	}
	
	return 0;
}
