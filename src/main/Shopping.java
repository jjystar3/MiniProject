package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//FileReader: 입력 기반 스트림
//FileReader fr = new FileReader("order.txt");

//BufferedReader: 보조스트림 (줄단위로 텍스트를 가져오는 기능)
//BufferedReader br = new BufferedReader(fr);

//한줄씩 가져오기
//String line = br.readLine();

public class Shopping {

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);

		loop: while (true) {
			System.out.println("1. 상품 주문하기");
			System.out.println("2. 전체 주문 이력 보기");
			System.out.println("3. 고객별 주문 이력 보기");
			System.out.println("4. 특정날짜에 들어온 주문이력 보기");
			System.out.println("5. 끝내기");
			System.out.print("옵션을 선택하세요: ");

			int value = scanner.nextInt();

			switch (value) {
			case 1:
				WriteOrder();
				break;
			case 2:
				ReadOrder();
				break;
			case 3:
				SearchByName();
				break;
			case 4:
				break;
			case 5:
				System.out.println("프로그램을 종료합니다...");
				break loop;
			}

		}

	}

	public static void WriteOrder() throws IOException {

		FileWriter fw = new FileWriter("order.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);

		Scanner scanner = new Scanner(System.in);

		System.out.print("고객명: ");
		String name = scanner.nextLine();
		System.out.print("제품명: ");
		String product = scanner.nextLine();
		System.out.print("제품의수량: ");
		int num = scanner.nextInt();
		System.out.print("제품의가격: ");
		int price = scanner.nextInt();

		Order order = new Order(name, product, num, price);

		LocalDateTime curDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDate = curDateTime.format(formatter);

		int lines = 0;
		while (br.readLine() != null)
			lines++;
		br.close();

		bw.write("주문번호: " + (lines + 1) + ", ");
		bw.write(order.toString());
		bw.write(", 주문일시: " + formatDate);
		bw.newLine();

		System.out.println("주문이 완료되었습니다!");

		bw.flush();
		bw.close();
	}

	public static void ReadOrder() throws IOException {

		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();

		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		br.close();
	}
	
	public static void SearchByName() throws IOException {

		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		Scanner scanner = new Scanner(System.in);

		System.out.print("고객명: ");
		String name = scanner.nextLine();
		
		String line = br.readLine();

		int orderCount = 0;
		int priceCount = 0;
		
		String str1 = "가격: ";
		String str2 = ", 주문일시";
		
		while (line != null) {
			if(line.contains(name + ",")) {
				orderCount++;
				int a = line.indexOf(str1) + str1.length();
				int b = line.indexOf(str2);
				priceCount += Integer.valueOf(line.substring(a, b));
			}
			line = br.readLine();
		}
		
		br.close();
		
		System.out.println("전체 주문 건수: " + orderCount);
		System.out.println("전체 주문 금액: " + priceCount);
		
	}
	
	public static void SearchByDate() throws IOException {

		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		Scanner scanner = new Scanner(System.in);

		System.out.print("날짜: ");
		String date = scanner.nextLine();

		
		String str = "주문일시: ";
		
		
		
		
		
		String line = br.readLine();

		while (line != null) {

			int a = line.indexOf(str) + str.length();
//			String[] strArr = str2.split(":");
			
			line = br.readLine();
		}
		br.close();
	}

}

class Order {
	String name;
	String product;
	int num;
	int price;

	public Order(String name, String product, int num, int price) {
		super();
		this.name = name;
		this.product = product;
		this.num = num;
		this.price = price;
	}

	@Override
	public String toString() {
		return "고객명: " + name + ", 제품명:" + product + ", 주문수량: " + num + ", 가격: " + price;
	}

}
