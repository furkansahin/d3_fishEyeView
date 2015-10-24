import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sahinfurkan on 23/10/15.
 */
public class write {
    public static void main(String[] args) throws IOException {
        String res = "{\"nodes\":[";
        HashMap<String, Integer> indexes = new HashMap<>();
        HashMap<Integer, String> indexesKey = new HashMap<>();
        int a = 0;
        ArrayList<String> sts = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get("/Users/sahinfurkan/Desktop/grades.csv"))){
            String node = "";
            String[] tmp = line.split(",");
            System.out.println(a);
        //    if (!tmp[9].equals("0")) {
                indexes.put(tmp[0], a++);
                indexesKey.put(a-1, tmp[0]);
                node += "{\"id\":\"" + tmp[0] + "\",\n";
                node += "\"name\":\"\",\n";
                node += "\"genre\":\"" + tmp[9] + "\",\n";
                node += "\"price\":\"" + tmp[8] + "\",\n";
                node += "\"producer\":\"\",\n";
                node += "\"name2\":\"\",\n";
                node += "\"name3\":\"\",\n";
                node += "\"servingNote\":\"\",\n";
                node += "\"productionNote\":\"\",\n";
                node += "\"degustationNote\":\"\",\n";
                node += "\"storageNote\":\"\",\n";
                node += "\"matchingFood\":\"\",\n";
                node += "\"sellArgument\":\"\",\n";
                node += "\"family\":\"\",\n";
                node += "\"rating\":\"\",\n";
                node += "\"region\":\"\",\n";
                node += "\"country\":\"\",\n";
                node += "\"grapeTypes\":\"\"},\n";
                if (sts.size() <= a / 100) {
                    sts.add(a / 100, node);
                } else {
                    sts.set(a / 100, sts.get(a / 100) + node);
                }
         //   }
        }
        for (int i = 0; i < sts.size(); i++){
            res += sts.get(i);
        }
        System.out.println("HEY//////////////////////////////////////////////////////////////");
        res = res.substring(0, res.length()-3);
        res += "}";
        System.gc();

        HashMap<Integer, Pair<Integer, String>> forumThreads = getForumThreads(indexesKey);
        HashMap<Integer, HashMap<Integer, Short>> forumRelations = getForumRelations(forumThreads, indexesKey);
        int b = 0;
        res += "],\"links\":[";
        for (Pair<Integer, String> pair : forumThreads.values()){

            int owner = pair.getLeft();
            HashMap<Integer, Short> map = forumRelations.get(owner);
            if (map != null){
                System.out.println(b++);
                Set<Integer> myKeyz = map.keySet();
                List<Integer> myKeys = new ArrayList<Integer>(myKeyz);

                for (int i = 0; i < map.size(); ++i ){
                    res += "{\"source\":" + myKeys.get(i) + ",\"target\":" + owner + ",\"value\":" + map.get(myKeys.get(i)) + "},\n";
                }
            }
        }
        res = res.substring(0, res.length()-3);
        res += "]}";


        PrintWriter writer = new PrintWriter("allData.json", "UTF-8");
        writer.println(res);
        writer.close();
        return;
    }
    public static HashMap<Integer, Pair<Integer, String>> getForumThreads(HashMap<Integer, String> indexes){
        HashMap<Integer, Pair<Integer, String>> forumThreads = new HashMap<Integer, Pair<Integer, String>>();
        try{
            List<String> allThreads = Files.readAllLines(Paths.get("/Users/sahinfurkan/Desktop/forum_threads.csv"));
            for (String count: allThreads){
                String[] tmp = count.split(",");
                if (indexes.containsKey(Integer.parseInt(tmp[2]))) {
                    forumThreads.put(Integer.parseInt(tmp[0]), new Pair(Integer.parseInt(tmp[2]), tmp[17]));
                }
            }
        }
        catch(Exception e){
        }
        return forumThreads;
    }

    public static HashMap<Integer, HashMap<Integer, Short>> getForumRelations(HashMap<Integer, Pair<Integer, String>> threads, HashMap<Integer, String> indexes) {
        HashMap<Integer, HashMap<Integer, Short>> forumRelations = new HashMap<Integer, HashMap<Integer, Short>>();
        try {
            List<String> allPosts = Files.readAllLines(Paths.get("/Users/sahinfurkan/Desktop/forum_posts.csv"));
            for (String post : allPosts) {
                String[] tmp = post.split(",");
                int thread_id = Integer.parseInt(tmp[1]);
                int commenter_id = Integer.parseInt(tmp[2]);
                if (threads.containsKey(thread_id)) {
                    int owner_id = threads.get(thread_id).getLeft();
                    if (indexes.containsKey(commenter_id) && indexes.containsKey(owner_id)) {
                        if (commenter_id != owner_id) {
                            if (forumRelations.containsKey(owner_id)) {
                                if (forumRelations.get(owner_id).containsKey(commenter_id)) {
                                    forumRelations.get(owner_id).put(commenter_id, (short) 2);
                                } else {
                                    forumRelations.get(owner_id).put(commenter_id, (short) 1);
                                }
                            } else if (forumRelations.containsKey(commenter_id)) {
                                if (forumRelations.get(commenter_id).containsKey(owner_id)) {
                                    forumRelations.get(commenter_id).put(owner_id, (short) 2);
                                } else {

                                    forumRelations.get(commenter_id).put(owner_id, (short) 1);
                                }
                            } else {
                                forumRelations.put(owner_id, new HashMap<>());
                                forumRelations.get(owner_id).put(commenter_id, (short) 1);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return forumRelations;
    }


}
