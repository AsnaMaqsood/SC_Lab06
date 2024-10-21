package twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialNetwork {

    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        // An empty Map is created that will serve as the graph
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        // This is the pattern that will be used to identify the usernames
        // Any word that begins with @ will be considered a username
        Pattern usernamePattern = Pattern.compile("@(\\w+)");
        
        // A loop will iterate over the tweets list and the usernamePattern will be used
        // to check if there is any mention in the person's tweets
        for (Tweet tweet : tweets) {
            // Convert the author's name to all uppercase
            String user = tweet.getAuthor().toUpperCase();
            
            // Retrieve the tweet text
            String userTweet = tweet.getText();
            
            // Create a matcher to find mentions in the tweet
            Matcher match = usernamePattern.matcher(userTweet);
            
            // Create a set to store mentioned users
            Set<String> mentionedUsers = new HashSet<>();
            
            while (match.find()) {
                // Extract the mentioned username and capitalize it
                String mentionedUser = match.group(1).toUpperCase();
                
                // Avoid adding the user's own mention
                if (!mentionedUser.equals(user)) {
                    mentionedUsers.add(mentionedUser);
                }
            }
            
            // If the user has mentioned others, add them to the graph
            if (!mentionedUsers.isEmpty()) {
                followsGraph.putIfAbsent(user, new HashSet<>()); // Ensure the user's entry exists
                followsGraph.get(user).addAll(mentionedUsers); // Add all mentioned users
            }
        }

        return followsGraph;
    }

    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        // A map using username as key, and the number of followers as the value is created
        Map<String, Integer> followersCountMap = new HashMap<>();

        // The followsGraph is used, and each user is iterated over so that we can count the number of followers
        for (String user : followsGraph.keySet()) {
            // For each user, the following list is iterated over
            for (String following : followsGraph.get(user)) {
                // The number of followers of the user is incremented accordingly
                followersCountMap.put(following, followersCountMap.getOrDefault(following, 0) + 1);
            }
        }

        // Create a list of usernames and sort it by follower count in descending order
        List<String> userNamesInDescendingOrderOfFollowerCount = new ArrayList<>(followersCountMap.keySet());

        // The Usernames are added to the List in Descending Order
        userNamesInDescendingOrderOfFollowerCount.sort((u1, u2) -> {
            // Compare follower counts
            int countComparison = Integer.compare(followersCountMap.getOrDefault(u2, 0), followersCountMap.getOrDefault(u1, 0));
            if (countComparison != 0) {
                return countComparison; // Sort by follower count
            }
            return u1.compareTo(u2); // Alphabetically for ties
        });

        return userNamesInDescendingOrderOfFollowerCount;
    }
}
