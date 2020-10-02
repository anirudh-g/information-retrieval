package uk.ac.gla.dcs.dsms;

import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.Posting;
import org.terrier.matching.dsms.DependenceScoreModifier;
// Anirudh Gokulaprasad - 2444267G
public class AnirudhGokulaprasad_MinDist extends DependenceScoreModifier {
	// This Java class implements the Min_Dist proximity feature
	@Override
	protected double calculateDependence(
			Posting[] ips, //postings for this document (these are actually IterablePosting[])
			boolean[] okToUse,  //is each posting on the correct document?
			double[] phraseTermWeights, boolean SD //not needed
		) 
	{
		
		final int numberOfQueryTerms = okToUse.length;
		double score = 0;
		int pairWiseCount = 0;
		for(int i = 0; i < numberOfQueryTerms; i++){
			if(okToUse[i]){
				BlockPosting blockPostingA = (BlockPosting) ips[i]; //casting postings to BlockPostings
				int[] termPositionsA = blockPostingA.getPositions(); //obtaining positions of term A
				int lenA = termPositionsA.length;

				for(int j=1; j< numberOfQueryTerms; j++)
				{
					if(okToUse[j])
					{
						BlockPosting blockPostingB = (BlockPosting) ips[j]; //casting postings to BlockPostings
						int[] termPositionsB = blockPostingB.getPositions(); //obtaining positions B
						int lenB = termPositionsB.length;

							double minDist = Double.MAX_VALUE; 

							for(int k = 0; k < lenA; k++){
								for(int m = k+1; m <lenB; m++){
									int temp = Math.abs(termPositionsB[m]- termPositionsA[k]); //Absolute difference of term positions
									if(minDist > temp) //finding minDist
										minDist = temp;

								}
							}
							pairWiseCount++;
							score += minDist;

						}
				}
			}


		}
		if(pairWiseCount ==0)
		{
			return 0;
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
		return "MinDist";
	}
}
