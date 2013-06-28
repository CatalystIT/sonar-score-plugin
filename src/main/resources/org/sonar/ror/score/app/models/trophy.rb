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
   
  end
  
   #saves trophies created by the admin
   def saveTrophy()
    @name = parseTrophy()  
    @propertyFound = is_property_new?()
    @validData = is_valid_data?()  
   
    #if the global trophy does not exist in the database...persist the property and trophy name 
    if (@propertyFound.blank?)
      
        if (@validData && validate_number(@amount) && validate_number(@duration))    
          Property.set(@propertyValue , @name)
        end
      
    else
    #if the global trophy property was found, then add the current trophy to the existing trophies        
      #finds the row with the global property
      @trophyValues = Property.find(:all, :conditions => {:prop_key => @propertyValue});
      @trophyPropertyValue= @trophyValues[0].text_value
      #create an array of all the existing trophies
      @trophyArray = @trophyPropertyValue.split(",")      
      @trophyArray.push(@name)  
      if (@validData && validate_number(@amount) && (@duration))     
        Property.set(@propertyValue, @trophyArray)
      end
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
  
  #checking to see if the amount entered is a number
  def validate_number(amount)
    reg = /^[1-9]\d*(\.\d+)?$/
    return (amount.match(reg))? true: false    
  end
    
  #makes sure all the form values are not blank
   def is_valid_data?()
    @validData = false
    name = @name
    metric = @metric
    amount = @amount
    isNumber = validate_number(amount)
    duration = @duration
    
    unless(name.blank? | metric.blank? | amount.blank? | duration.blank? )       
      @validData = true     
    end
     
   end
 
end
