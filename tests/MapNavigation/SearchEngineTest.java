package MapNavigation;

import Database.AdminLogManager;
import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchEngineTest {

    @Test
    public void testCafe(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        SearchEngine se = new SearchEngine(manager);
        List<Node> answer = (se.Search("Cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);

    }

    @Test
    public void testLowerCafe(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        SearchEngine se = new SearchEngine(manager);
        List<Node> answer = (se.Search("cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);

    }

    @Test
    public void twice(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();
        
        SearchEngine se = new SearchEngine(manager);
        List<Node> answer = (se.Search("cafe"));
        List<String> names = new ArrayList<>();
        for(Node n: answer){
            names.add(n.getShortName());
        }

        System.out.println(names);


        List<Node> answer2 = (se.Search("ATM"));
        List<String> names2 = new ArrayList<>();
        for(Node n: answer2){
            names2.add(n.getShortName());
        }

        System.out.println(names2);
    }

}