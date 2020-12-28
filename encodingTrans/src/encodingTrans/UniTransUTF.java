package encodingTrans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniTransUTF {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName = "C:\\Users\\Z4\\git\\scairo-base\\src\\com\\sc\\scairo\\core\\xml\\"; // ���� ��� ����
		ListFile(fileName);

	}

	private static void ListFile(String fileName) {
		// FileReader bufferedReader = null;
		int i = 0;
		try {
			File path = new File(fileName);
			File[] fList = path.listFiles();
			for (; i < fList.length; i++)
			{
				if (fList[i].isFile() && ( fList[i].getName().endsWith(".java") || fList[i].getName().endsWith(".properties")) )
				{

					Path filePath = Paths.get(fList[i].getPath()); // �ش� java����
					BufferedReader bufferedReader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);

					String line;
					boolean writeTime = false;

					StringBuffer sb = new StringBuffer();
					while ((line = bufferedReader.readLine()) != null) {
						for (int j = 0; j < line.length(); j++) {
							if ('\\' == line.charAt(j) && j+1<line.length() ) {
								if('u' == line.charAt(j + 1)) {
									// �� �� �ױ��ڴ� �����ڵ��� 16���� �ڵ��̴�. int������ �ٲپ �ٽ� char Ÿ������ ���� ��ȯ�Ѵ�.
									Character r = (char) Integer.parseInt(line.substring(j + 2, j + 6), 16);
									// ��ȯ�� ���ڸ� ���ۿ� �ִ´�.
									sb.append(r);

									// for�� ���� �� 1�� 5�� ���� 6���ڸ� ����
									j += 5;

									writeTime = true;
								}
							} else {
								// ascii�ڵ�� �״�� ���ۿ� �ִ´�.
								sb.append(line.charAt(j));

							}

						}
						sb.append("\n");

					}
					if(writeTime) {
						BufferedWriter writer = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(fList[i].getPath()), "UTF8"));

						writer.write(sb.toString());
						writer.flush();	
						writer.close();
					}
					bufferedReader.close();

				} else if (fList[i].isDirectory()) {
					ListFile(fList[i].getPath()); // ����Լ� ȣ��
				}


			}
		}

		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ex) {
			System.out.println(ex);
		}
		catch(NumberFormatException ex){
			ex.printStackTrace();
		}
	}
}
