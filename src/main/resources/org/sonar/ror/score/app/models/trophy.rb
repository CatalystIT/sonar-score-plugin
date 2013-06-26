class Trophy < ActiveRecord::Base
  attr_accessor :name, :metric, :amount, :duration, :durationValue
    
   #initialize the variables 
  def initialize(trophy=nil)
    @name = trophy['name']
    @metric = trophy['metric']
    @amount = trophy['amount']
    @duration = trophy['duration']
    @durationValue = trophy['durationValue']
    @propertyValue = "sonar.score.Trophy"
    @trophyValues
    @trophyPropertyValue
    @trophyArray = Array.new
    @propertyFound
   
    
  end
  
   #saves trophies created by the admin
   def saveTrophy()          
    @propertyFound = is_property_new?()
    #if the global trophy does not exist in the database...persist the property and trophy name 
    if (@propertyFound.blank?)
    	@name = parseTrophy()
    	Property.set(@propertyValue , @name)
    else
    #if the global trophy property was found, then add the current trophy to the existing trophies	
    	@name = parseTrophy()
    	#finds the row with the global property
    	@trophyValues = Property.find(:all, :conditions => {:prop_key => @propertyValue});
    	@trophyPropertyValue= @trophyValues[0].text_value
    	#create an array of all the existing trophies
    	@trophyArray = @trophyPropertyValue.split(",")    	
    	@trophyArray.push(@name)    	
    	Property.set(@propertyValue, @trophyArray)
    end    
    
     
    end
   #checks to see if the global trophy property has been persisted
  def is_property_new?()
    Property.find(:all, :conditions => {:prop_key => @propertyValue})
  end
  
  #set the global trophy property text_value correctly so the different trophies can be parsed
  def parseTrophy()
    @name = @name + '{'+ @metric + ';' + @amount + ';' + @duration + @durationValue +'}'
  end
  
  
end
