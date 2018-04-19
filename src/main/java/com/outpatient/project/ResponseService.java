package com.outpatient.project;

import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;


public class ResponseService {

	public double[] getSummary(JsonNode rootNode, double[] result) {	
			JsonNode totalNode = rootNode.path(Constant.TOTAL);
			int total = totalNode.asInt(), pointsWithRatings = 0, totalReviews = 0, counter =0;
			double totalRating = 0;

			JsonNode businessNodes = rootNode.path(Constant.BUSINESSES);
			Iterator<JsonNode> elements = businessNodes.elements();
			while(elements.hasNext()){
				counter++;
				JsonNode businessNode = elements.next();
				JsonNode review = businessNode.path(Constant.REVIEW_COUNT);
				JsonNode rating = businessNode.path(Constant.RATING);
				totalReviews += review.asInt();
				if(rating != null) {
					pointsWithRatings++;
					totalRating += rating.asDouble();
				}
			}
			result[0] += counter;
			result[1] += pointsWithRatings;
			result[2] += totalRating/total;
			result[3] += totalReviews;

			return result;			
	}
	
	public List<Business> getSortedSummary(JsonNode rootNode, List<Business> businessList) {
			JsonNode businessNodes = rootNode.path(Constant.BUSINESSES);
			Iterator<JsonNode> elements = businessNodes.elements();
			while(elements.hasNext()){
				JsonNode businessNode = elements.next();
				JsonNode nameNode = businessNode.path(Constant.NAME);
				JsonNode ratingNode = businessNode.path(Constant.RATING);
				JsonNode reviewNode = businessNode.path(Constant.REVIEW_COUNT);	
				JsonNode locationNode = businessNode.path(Constant.LOCATION);
				JsonNode addressNode = locationNode.path(Constant.DISPLAY_ADDRESS);
				Iterator<JsonNode> addresselements = addressNode.elements();
				StringBuilder address = new StringBuilder();
				while(addresselements.hasNext()) {
					JsonNode node  = addresselements.next();
					address.append(node.asText());
				}
				if(ratingNode != null) {
					Business business = new Business(nameNode.asText(), address.toString(), 
							reviewNode.asInt(), ratingNode.asInt());
					businessList.add(business);
				}
			}
			return businessList;
	}
	
}
