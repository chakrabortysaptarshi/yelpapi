package com.outpatient.project;


import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;


public class ResponseService {

	public double[] getSummary(JsonNode rootNode, double[] result) {
					
					JsonNode totalNode = rootNode.path("total");
					int total = totalNode.asInt(), pointsWithRatings = 0, totalReviews = 0;
					double totalRating = 0;
					

					JsonNode phoneNosNode = rootNode.path("businesses");
					Iterator<JsonNode> elements = phoneNosNode.elements();
					int counter = 0;
					while(elements.hasNext()){
						counter++;
						JsonNode phone = elements.next();
						JsonNode review = phone.path("review_count");
						JsonNode rating = phone.path("rating");
						
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
		
			
			JsonNode phoneNosNode = rootNode.path("businesses");
			Iterator<JsonNode> elements = phoneNosNode.elements();
			while(elements.hasNext()){
				JsonNode phone = elements.next();
				JsonNode nameNode = phone.path("name");
				JsonNode ratingNode = phone.path("rating");
				JsonNode reviewNode = phone.path("review_count");
				
				JsonNode locationNode = phone.path("location");
				JsonNode addressNode = locationNode.path("display_address");
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
