package uk.ac.gla.dcs.dsms;

import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.Posting;
import org.terrier.matching.dsms.DependenceScoreModifier;
//Anirudh Gokulaprasad - 2444267G
public class AnirudhGokulaprasad_DiffAvgPos extends DependenceScoreModifier{
	// This Java class implements the Diff_Avg_Pos proximity feature
	@Override
	protected double calculateDependence(
			Posting[] ips, //postings for this document (these are actually IterablePosting[])
			boolean[] okToUse,  //is each posting on the correct document?
			double[] phraseTermWeights, boolean SD //not needed
		) 
	{
		
		final int numberOfQueryTerms = okToUse.length;
		double score = 0;
		double pairWiseCount =0;
		for(int i=0; i<numberOfQueryTerms; i++)
		{
			if(okToUse[i])
			{
				BlockPosting blockPostingA = (BlockPosting) ips[i]; //casting postings to BlockPostings
				int[] termPositionsA = blockPostingA.getPositions(); //obtaining positions of term A
				int lenA = termPositionsA.length;
				
				for(int j=i+1; j< numberOfQueryTerms; j++)
				{
					if(okToUse[j])
					{
						BlockPosting blockPostingB = (BlockPosting) ips[j]; //casting postings to BlockPostings
						int[] termPositionsB = blockPostingB.getPositions(); //obtaining positions B
						int lenB = termPositionsB.length;
						 int sumA = 0;
                         int sumB = 0;
                         for(int k = 0; k < lenA; k++){
                             sumA += termPositionsA[k];
                         }
                         
                         for(int m = 0; i < lenB; i++){
                             sumB += termPositionsB[m];
                         }
                         double avgA = sumA/lenA; //Avg Position of term A
                         double avgB = sumB/lenB; // Avg Position of term B
                         score += Math.abs(avgA - avgB); //Difference in Avg Postion of A and B
                         pairWiseCount++;
					}
				}
			}
		}
		if (pairWiseCount==0) //Returns score only for query pairs
		{
			return 0.0d;
		}
		else
		{
			return score;
		}
		
	}

	/** You do NOT need to implement this method */
	@Override
	protected double scoreFDSD(int matchingNGrams, int docLength) {
		throw new UnsupportedOperationException();
	}


	@Override
	public String getName() {
		return "DiffAvgPos";
	}
	

}
