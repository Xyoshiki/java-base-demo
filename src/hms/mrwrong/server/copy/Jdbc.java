package hms.mrwrong.server.copy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdbc {
	// ��
	public static List<Map<String, Object>> query(String sql,Object... args) throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> data = new ArrayList<>();
		// 1.��������
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "");) {
			// 2.��ȡ����

			try (PreparedStatement statement = connection.prepareStatement(sql); // 3.����sql
					ResultSet rs = statement.executeQuery();) {
				// 4.��������
				ResultSetMetaData metaData = rs.getMetaData();

				while (rs.next()) {
					Map<String, Object> tempMap = new HashMap<>();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						tempMap.put(metaData.getColumnLabel(i), rs.getObject(i));
					}

					data.add(tempMap);
				}
			}

		}
		// System.out.println(data);
		return data;
		// 5.�ر����ӣ������try��
		// ��Connection
		// ��PreparedStatement
		// ��ResultSet
	}

	// ��ɾ��
	public static int updata(String sql, Object... args) throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> data = new ArrayList<>();
		// 1.��������
		Class.forName("com.mysql.jdbc.Driver");
		try (// 2.��ȡ����
				Connection connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "");) {

			try (// 3.����sql
					PreparedStatement statement = connection.prepareStatement(sql)) {
				for (int i = 0; i < args.length; i++) {

					statement.setObject(i + 1, args[i]);
				}

				// 4.��������
				int lines = statement.executeUpdate();
				return lines;

			}
		}
	}

}
