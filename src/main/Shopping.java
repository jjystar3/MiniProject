package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Shopping {

	public static void main(String[] args) throws IOException {

		OrderProduct orderProduct = new OrderProduct();
		SearchOrders searchOrders = new SearchOrders();

		String FILE_NAME = "order.txt";

		Scanner scanner = new Scanner(System.in);

		loop: while (true) {
			System.out.println("1. 상품 주문하기");
			System.out.println("2. 전체 주문 이력 보기");
			System.out.println("3. 고객별 주문 이력 보기");
			System.out.println("4. 특정날짜에 들어온 주문이력 보기");
			System.out.println("5. 끝내기");
			System.out.print("옵션을 선택하세요: ");

			int value = scanner.nextInt();
			scanner.nextLine();

			switch (value) {
			case 1:
				orderProduct.WriteOrder(FILE_NAME, scanner);
				break;
			case 2:
				orderProduct.ReadOrder(FILE_NAME);
				break;
			case 3:
				searchOrders.SearchByName(FILE_NAME, scanner);
				break;
			case 4:
				searchOrders.SearchByDate(FILE_NAME, scanner);
				break;
			case 5:
				System.out.println("프로그램을 종료합니다...");
				break loop;
			}

		}

	}

}

class OrderProduct {

	void WriteOrder(String FILE_NAME, Scanner scanner) throws IOException {

		FileWriter fw = new FileWriter(FILE_NAME, true);
		BufferedWriter bw = new BufferedWriter(fw);
		FileReader fr = new FileReader(FILE_NAME);
		BufferedReader br = new BufferedReader(fr);

		System.out.print("고객명: ");
		String name = scanner.nextLine();
		System.out.print("제품명: ");
		String product = scanner.nextLine();
		System.out.print("제품의수량: ");
		int num = scanner.nextInt();
		System.out.print("제품의가격: ");
		int price = scanner.nextInt();

		int lines = 0;
		while (br.readLine() != null)
			lines++;
		br.close();

		String str = String.format("고객명: %s, 제품명: %s, 주문수량: %d, 가격: %d", name, product, num, price);

		LocalDateTime curDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDate = curDateTime.format(formatter);

		bw.write("주문번호: " + (lines + 1) + ", " + str + ", 주문일시: " + formatDate);
		bw.newLine();

		System.out.println("주문이 완료되었습니다!");

		bw.flush();
		bw.close();
	}

	void ReadOrder(String FILE_NAME) throws IOException {

		FileReader fr = new FileReader(FILE_NAME);
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		br.close();
	}

}

class SearchOrders {

	void SearchByName(String FILE_NAME, Scanner scanner) throws IOException {

		FileReader fr = new FileReader(FILE_NAME);
		BufferedReader br = new BufferedReader(fr);

		System.out.print("고객명: ");
		String name = scanner.nextLine();

		int orderCount = 0;
		int priceCount = 0;

		String str0 = ", 주문수량: ";
		String str1 = ", 가격: ";
		String str2 = ", 주문일시";

		String line = br.readLine();

		while (line != null) {
			if (line.contains(" " + name + ",")) {
				orderCount++;
				int orders = Integer.valueOf(line.substring(line.indexOf(str0) + str0.length(), line.indexOf(str1)));
				priceCount += orders
						* Integer.valueOf(line.substring(line.indexOf(str1) + str1.length(), line.indexOf(str2)));
			}
			line = br.readLine();
		}

		br.close();

		System.out.println("전체 주문 건수: " + orderCount);
		System.out.println("전체 주문 금액: " + priceCount);

	}

	void SearchByDate(String FILE_NAME, Scanner scanner) throws IOException {

		FileReader fr = new FileReader(FILE_NAME);
		BufferedReader br = new BufferedReader(fr);

		System.out.print("날짜: ");
		String getDate = scanner.nextLine();

		LocalDate date2 = LocalDate.parse(getDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String str = "주문일시: ";

		String line = br.readLine();

		while (line != null) {
			String readDate = line.substring(line.indexOf(str) + str.length(), line.length());
			LocalDateTime dateTime = LocalDateTime.parse(readDate, formatter);
			LocalDate date = dateTime.toLocalDate();

			if (date.equals(date2)) {
				System.out.println(line);
			}

			line = br.readLine();
		}
		br.close();
	}

}