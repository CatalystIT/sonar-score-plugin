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
    @currentProperty
    @criteria
       
  end
    
   #saves Trophy(s) or Title Cup(s) created by the admin
   def saveTrophy()
    if (@type == "Trophy")
      @propertyValue = "sonar.score.Trophy"
    elsif (@type == "Title Cup")
      @propertyValue = "sonar.score.TitleCup"
    end
    
    @name = formatName()
    #this is the property that will be persisted to the database
    @currentProperty =   @propertyValue + ':' + @name    
    @propertyFound = is_property_new?()
    @validData = is_valid_data?()
    @criteria = parseCriteria()  
    @textValue = @currentProperty + ',' + @criteria 
   
    #if the trophy/title cup does not exist in the database...persist the new property 
    if (@propertyFound.blank?)
      
        if (@validData && validate_number(@amount) && validate_number(@duration))    
           Property.set(@currentProperty , @criteria)
        end
      
    else
    #if the trophy/title cup property was found, then add the additional criteria to the trophy/title cup 
      prop = Property.by_key(@currentProperty, nil, nil)           
         if (@validData && validate_number(@amount) && (@duration))
           newTextValue = prop.text_value.to_s + ',' + @criteria           
           prop.text_value = newTextValue
           prop.save
           
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
  
  #checks to see if the trophy/title cup property has been persisted
  def is_property_new?()
    Property.find(:all, :conditions => {:prop_key => @currentProperty})
  end
  
  #set the trophy/title cup property text_value correctly so the different trophies/title cups can be parsed
  def parseCriteria()
    if (@amount.blank? | @duration.blank? | @durationValue.blank?)
      @criteria = '{' + @metric + '}'
    else
      @criteria ='{'+ @metric + ';' + @amount + ';' + @duration + @durationValue +'}'
    end
  end
  
    
  #checking to see if the amount entered is a number
  def validate_number(amount)
    if (@type == "Trophy")
      reg = /^[1-9]\d*(\.\d+)?$/
      return (amount.match(reg))? true: false  
    else
      return true
    end  
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
    
    if (@type == "Trophy")
      unless(name.blank? | metric.blank? | amount.blank? | duration.blank? | durationValue.blank? | type.blank?)       
        @validData = true 
      end
    elsif(@type =="Title Cup")
      unless (name.blank? | metric.blank?)
        @validData = true
      end   
    end
     
   end
 
end
