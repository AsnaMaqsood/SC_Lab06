package twitter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.*;

public class SocialNetworkTest {

	//Note:  Necessary list for each test cases are created
	
	
	//Test Case 1
	@Test
	public void emptyListOfTweets() {
		List<Tweet> tweetsList=Collections.emptyList();
		Map<String, Set<String>> followersGraph=SocialNetwork.guessFollowsGraph(tweetsList);
		assertTrue(followersGraph.isEmpty(), "Empty Graph should be returned");
	}
	
	
	
	//Test Case 2
	@Test
	public void tweetsWithoutMentions() {
		 List<Tweet> tweets = Arrays.asList(
		            new Tweet(1, "Violet", "Hellooo!", Instant.now()),
		            new Tweet(2, "Klaus", "How are you?", Instant.now()),
		            new Tweet(3, "Sunny", "I'm fine!", Instant.now())
		 );
			Map<String, Set<String>> followersGraph=SocialNetwork.guessFollowsGraph(tweets);
			assertTrue(followersGraph.isEmpty(), "Empty Graph should be returned");
	}
	
	
	//Test Case 3
	@Test
	public void singleMention() {
		List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "@Klaus Hellooo!", Instant.now())
	 );
		Map<String, Set<String>> followersGraph=SocialNetwork.guessFollowsGraph(tweets);
		assertEquals(1, followersGraph.size(), "Graph should have 1 username");
		assertTrue(followersGraph.containsKey("VIOLET"), "The username VIOLET should show");
        assertTrue(followersGraph.get("VIOLET").contains("KLAUS"), "VIOLET should follow KLAUS");		
	}
	
	
	//Test Case 4
	@Test
	public void multipleMentionsByUser() {
		List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "@Klaus @Sunny Hellooo!", Instant.now())
	 );
		Map<String, Set<String>> followersGraph=SocialNetwork.guessFollowsGraph(tweets);
		assertEquals(1, followersGraph.size(), "Graph should have 1 username");
		assertTrue(followersGraph.containsKey("VIOLET"), "The username VIOLET should show");
        assertTrue(followersGraph.get("VIOLET").contains("KLAUS"), "VIOLET should follow KLAUS");
        assertTrue(followersGraph.get("VIOLET").contains("SUNNY"), "VIOLET should follow SUNNY");		

	}
	

	//Test Case 5
		@Test
		public void multipleMentionsOfSameUser() {
			List<Tweet> tweets = Arrays.asList(
		            new Tweet(1, "Violet", "@Klaus Hellooo!", Instant.now()),
		            new Tweet(1, "Violet", "@Klaus How are you??", Instant.now()),
		            new Tweet(1, "Violet", "@Klaus How's it going?", Instant.now())
		 );
			Map<String, Set<String>> followersGraph=SocialNetwork.guessFollowsGraph(tweets);
			assertEquals(1, followersGraph.size(), "Graph should have 1 username");
			assertTrue(followersGraph.containsKey("VIOLET"), "The username VIOLET should show");
	        assertTrue(followersGraph.get("VIOLET").contains("KLAUS"), "VIOLET should follow KLAUS");		

		}

		
		
		//Test Case 6
		@Test
		public void emptyInfluencersGraph() {
			List<Tweet> tweets = Collections.emptyList();
		    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
	        List<String> Influencers = SocialNetwork.influencers(followsGraph);
	        assertTrue(Influencers.isEmpty(), "The influencer list should be empty for an empty graph.");
		}
		
		
		
		
		//Test Case 7
		@Test
	    public void singleUserWithoutFollowers() {
	        List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "Hello!!", Instant.now())
	        );

	        Map<String, Set<String>> followersGraph = SocialNetwork.guessFollowsGraph(tweets);
	        List<String> influencers = SocialNetwork.influencers(followersGraph);
	        assertTrue(influencers.isEmpty(), "The list should be empty!");
	    }
		
		
		// Test Case 8
	    @Test
	    public void singleInfluencer() {
	        List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "@Klaus Hello", Instant.now()),
	            new Tweet(2, "Klaus", "Hello Violet!", Instant.now())
	        );

	        Map<String, Set<String>> followersGraph = SocialNetwork.guessFollowsGraph(tweets);
	        List<String> influencers = SocialNetwork.influencers(followersGraph);

	        assertEquals(1, influencers.size(), "One influencer only");
	        assertEquals("KLAUS", influencers.get(0), "KLAUS should be the influencer");
	    }

	    
	    
		// Test Case 9
	    @Test
	    public void multipleInfluencers() {
	        List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "@Sunny @Klaus Hey!", Instant.now()),
	            new Tweet(2, "Klaus", "@Sunny Good day!", Instant.now()),
	            new Tweet(3, "Sunny", "@Violet Hello!", Instant.now())
	        );

	        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
	        List<String> influencers = SocialNetwork.influencers(followsGraph);

	        assertEquals(3, influencers.size(), "There should be three influencers.");
	        assertEquals("SUNNY", influencers.get(0), "SUNNY should be the most influential.");
	        assertEquals("KLAUS", influencers.get(1), "KLAUS should be the second influencer.");
	        assertEquals("VIOLET", influencers.get(2), "VIOLET should be the third influencer.");
}
	    
	    
	    
	 // Test Case 10
	    @Test
	    public void tiedInfluence() {
	        List<Tweet> tweets = Arrays.asList(
	            new Tweet(1, "Violet", "@Klaus @Sunny", Instant.now()),
	            new Tweet(2, "Klaus", "@Sunny @Violet", Instant.now()),
	            new Tweet(3, "Sunny", "@Violet @Klaus", Instant.now())
	        );

	        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
	        List<String> influencers = SocialNetwork.influencers(followsGraph);

	        assertEquals(3, influencers.size(), "There should be three influencers.");
	        assertTrue(influencers.contains("VIOLET"), "Violet should be one of the influencers.");
	        assertTrue(influencers.contains("KLAUS"), "Klaus should be one of the influencers.");
	        assertTrue(influencers.contains("SUNNY"), "Sunny should be one of the influencers.");
	    }
	    
	    
	    



}
