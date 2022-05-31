package opgg.opgg.dao;

import opgg.opgg.dto.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    private static String dburl = "";
    private static String dbUser = "";
    private static String dbpasswd = "";

    public int addSearchLog(User user) {
        int insertCount = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO search (profileIconId, name, puuid, id, accountId ) VALUES ( ?, ?, ?, ?, ? ) ON DUPLICATE KEY UPDATE searchdate = VALUES(searchdate);";

        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getProfileIconId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPuuid());
            ps.setString(4, user.getId());
            ps.setString(5, user.getAccountId());

            insertCount = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return insertCount;
    }

    public List<User> getSearchLogs() {
        List<User> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT profileIconId,name FROM search order by searchdate desc";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String name = rs.getString("name");
                    int profileIconId = rs.getInt("profileIconId");
                    User user = new User();
                    user.setProfileIconId(profileIconId);
                    user.setName(name);
                    list.add(user); // list에 반복할때마다 Role인스턴스를 생성하여 list에 추가한다.
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
