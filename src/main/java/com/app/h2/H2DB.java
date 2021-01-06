package com.app.h2;

import com.app.entity.Item;
import org.h2.tools.DeleteDbFiles;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class H2DB {


    public static void write(Item item) throws SQLException, ClassNotFoundException {

        String url = "jdbc:h2:file:C:\\Users\\vital\\IdeaProjects\\FastClean\\db;MV_STORE=false";
        var user = "idcard";
        var passwd = "idcard2017";


        DeleteDbFiles.execute("C:\\Users\\vital\\IdeaProjects\\FastClean\\db\\", "db", true);

        Connection conn = DriverManager.getConnection(url, user, passwd);
        Statement stat = conn.createStatement();

        LocalDate date = item.getDate();
        System.out.println(date);

//        stat.execute("create table test2(id int primary key, article varchar(255),sizes varchar(255),fio varchar(255),org varchar(255),city varchar(255),depart varchar(255),inv varchar(255),curr_date date,epc varchar(255),office varchar(255),otgr int)");
        stat.execute("insert into test1 values(" + item.getId() + ",'" + item.getVendorCode().toString() + "','" + item.getSize().toString() + "','" + item.getName() + "','" + item.getOrganization() + "','" + item.getCity() + "','" + item.getDepartment() + "','" + item.getInventoryCode() + "',PARSEDATETIME('" + item.getDate() + "', 'yyyy-MM-dd'),'" + item.getEpc() + "','" + item.getOfficeDepartment() + "', 0 )");

        stat.close();
        conn.close();
    }


    public static List<Item> getData() throws SQLException {
        String url = "jdbc:h2:file:C:\\Users\\vital\\IdeaProjects\\FastClean\\db;MV_STORE=false";
        var user = "idcard";
        var passwd = "idcard2017";

        Connection conn = DriverManager.getConnection(url, user, passwd);
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from test1");
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getInt("id");
            String article = rs.getString("article");

            String size = rs.getString("sizes");
            String fio = rs.getString("fio");
            String org = rs.getString("org");
            String city = rs.getString("city");
            String depart = rs.getString("depart");
            String inv = rs.getString("inv");
            Date curr_date = rs.getDate("curr_date");
            LocalDate date = curr_date.toLocalDate();
            String epc = rs.getString("epc");
            String office = rs.getString("office");
            int otgr = rs.getInt("otgr");
            Item item = new Item(id,article,size,fio,org,city,depart,inv,date,epc,office);
            items.add(item);
        }


        stat.close();
        conn.close();
        return items;
    }

    public static List<String> getOfficeDepartment() throws SQLException {
        String url = "jdbc:h2:file:C:\\Users\\vital\\IdeaProjects\\FastClean\\db;MV_STORE=false";
        var user = "idcard";
        var passwd = "idcard2017";

        Connection conn = DriverManager.getConnection(url, user, passwd);
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select office from test1");
        List<String> officeDepartment = new ArrayList<>();
        while (rs.next()) {
            String office = rs.getString("office");
            officeDepartment.add(office);
        }
        stat.close();
        conn.close();
        return officeDepartment;
    }

    public static Item getItemByEpc(String epc) throws SQLException {
        String url = "jdbc:h2:file:C:\\Users\\vital\\IdeaProjects\\FastClean\\db;MV_STORE=false";
        var user = "idcard";
        var passwd = "idcard2017";

        Connection conn = DriverManager.getConnection(url, user, passwd);
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from test1 where epc ='" +epc +"'");
        Item item = new Item();
        while (rs.next()) {
            long id = rs.getInt("id");
            String office = rs.getString("office");
            String article = rs.getString("article");
            String myEpc = rs.getString("epc");
            String size = rs.getString("sizes");
            String fio = rs.getString("fio");
            String org = rs.getString("org");
            String city = rs.getString("city");
            String depart = rs.getString("depart");
            String inv = rs.getString("inv");
            Date curr_date = rs.getDate("curr_date");
            LocalDate date = curr_date.toLocalDate();
           item = new Item(id,article,size,fio,org,city,depart,inv,date,office, myEpc);

        }
        stat.close();
        conn.close();
        return item;
    }


}