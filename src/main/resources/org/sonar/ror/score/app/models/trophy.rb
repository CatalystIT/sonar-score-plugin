class Trophy < ActiveRecord::Base
  attr_accessor :name, :metric, :amount, :duration, :durationValue, :type
    
   #initialize the variables 
  def initialize(trophy=nil)
    @type = trophy['type']
    @name = trophy['name']
    @metric = trophy['metric']
    @amount = trophy['amount']
    @duration = trophy['duration']
    @durationValue = trophy['durationValue']
    @propertyValue 
    @trophyValues
    @trophyPropertyValue
    @trophyArray = Array.new
   
  end
  
   #saves Trophy(s) or Title Cup(s) created by the admin
   def saveTrophy()
    if (@type == "Trophy")
      @propertyValue = "sonar.score.Trophy"
    elsif (@type == "Title Cup")
      @propertyValue = "sonar.score.TitleCup"
    end
    
    @name = formatName()
    @name = parseTrophy()  
    @propertyFound = is_property_new?()
    @validData = is_valid_data?()  
   
    #if the global trophy does not exist in the database...persist the property and trophy name 
    if (@propertyFound.blank?)
      
        if (@validData && validate_number(@amount) && validate_number(@duration))    
          Property.set(@propertyValue , @name)
        end
      
    else
    #if the global trophy property was found, then add the current trophy/title cup to the existing trophies/title cups       
      #finds the row with the global property
      @trophyValues = Property.find(:all, :conditions => {:prop_key => @propertyValue});
      @trophyPropertyValue= @trophyValues[0].text_value
      #create an array of all the existing trophies/title cups
      @trophyArray = @trophyPropertyValue.split(",")      
      @trophyArray.push(@name)  
        if (@validData && validate_number(@amount) && (@duration))             
          Property.set(@propertyValue, @trophyArray)
        end
      end    
    end
    
  #formats the title cup/trophy name to capitalize every word and removes spaces between words
  #i.e. : My best Trophy changes to MyBestTrophy
  def formatName()
    if (@name.match(/\s/))
      @name =  @name.split(' ').map(&:capitalize).join(' ').delete(' ')
    else
      @name = @name
    end 
   
  end
  
  #checks to see if the global trophy/title cup property has been persisted
  def is_property_new?()
    Property.find(:all, :conditions => {:prop_key => @propertyValue})
  end
  
  #set the global trophy property/title cup property text_value correctly so the different trophies/title cups can be parsed
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
    durationValue = @durationValue
    type = @type
    
    unless(name.blank? | metric.blank? | amount.blank? | duration.blank? | durationValue.blank? | type.blank?)       
      @validData = true     
    end
     
   end
 
end
