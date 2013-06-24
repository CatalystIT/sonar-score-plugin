module ImagesHelper

def create_trophy
  Property.set("sonar.score.projectTrophy", "No Trophies have been earned yet", 1 )	
end 
end