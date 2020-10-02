package uk.ac.gla.dcs.dsms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.terrier.indexing.IndexTestUtils;
import org.terrier.structures.Index;
import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.IterablePosting;
import org.terrier.tests.ApplicationSetupBasedTest;
import org.terrier.utility.ApplicationSetup;

//Anirudh Gokulaprasad - 2444267G

public class AnirudhGokulaprasad_TestDiffAvgPos extends ApplicationSetupBasedTest{
	
	@Test public void testOneDocTwoTerms() throws Exception {

		//make an index with a single sample document
		ApplicationSetup.setProperty("termpipelines", "");
		Index index = IndexTestUtils.makeIndexBlocks(
				new String[]{"doc1"}, 
				new String[]{"The tiger hunts"});
				//new String[]{"John is a good man"});
				//new String[]{"The lazy brown fox jumps"});
				//new String[]{"Prevention is better than cure"});
		//get posting iterators for two terms 'fox' and 'jumps'
		IterablePosting[] ips = new IterablePosting[3];
		ips[0] = index.getInvertedIndex().getPostings(index.getLexicon().getLexiconEntry("tiger"));
		ips[1] = index.getInvertedIndex().getPostings(index.getLexicon().getLexiconEntry("hunts"));
		//ips[2] = index.getInvertedIndex().getPostings(index.getLexicon().getLexiconEntry("lazy"));
		ips[0].next();
		ips[1].next();
		//ips[2].next();
		assertEquals(0, ips[0].getId());
		assertEquals(0, ips[1].getId());
		//assertEquals(0, ips[2].getId());
		System.out.println("Positions of term 'tiger'="+ Arrays.toString( ((BlockPosting)ips[0]).getPositions()));
		System.out.println("Positions of term 'hunts'="+ Arrays.toString( ((BlockPosting)ips[1]).getPositions()));
		//System.out.println("Positions of term 'lazy'="+ Arrays.toString( ((BlockPosting)ips[2]).getPositions()));
		
		AnirudhGokulaprasad_DiffAvgPos sample = new AnirudhGokulaprasad_DiffAvgPos();
		double score = sample.calculateDependence(
            ips, //posting lists
            new boolean[]{true,true},  //is this posting list on the correct document?
            new double[]{1d,1d}, false//doesnt matter
		);
		System.out.println(score);
		//TODO: make your assertion about what the score should be
		assertEquals(1.0, score, 0.0d);
	}
}
