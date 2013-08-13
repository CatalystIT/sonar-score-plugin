#	Copyright 2013 Catalyst IT Services
#
#	Licensed under the Apache License, Version 2.0 (the "License");
#	you may not use this file except in compliance with the License.
#	You may obtain a copy of the License at
#
#		http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

class Award < ActiveRecord::Base
  attr_accessor :name, :metric, :amount, :duration, :durationValue, :type, :trophyselect, :titlecupselect, :newbutton 
   
  CRITERIA_ALREADY_EXISTS = 2
  CRITERIA_CREATED = 1

   #initialize the variables 
  def initialize(award=nil)
    @type = award['type']
    @name = award['name']
    @metric = award['metric']
    @amount = award['amount']
    @duration = award['duration']
    @durationValue = award['durationValue']
    @propertyValue =nil
    @currentProperty
    @criteria       
    @trophyselect = award['trophyselect']
    @cupselect = award['titlecupselect']
    @currentExistingName
    @existing = award['newbutton']
  end
    
   #saves Trophy(s) or Title Cup(s) created by the admin
   def saveTrophy()
      unless(@type.blank?)
      if (@type == "Trophy")
        @propertyValue = "sonar.score.Trophy"
        @currentExistingName = @trophyselect
      elsif (@type == "Title Cup")
        @propertyValue = "sonar.score.TitleCup"
        @currentExistingName = @cupselect
      end
   
      if (@existing == "New")
        @name = formatName()        
      elsif (@existing == "Existing")
        @name = formatExistingName()
      end
    
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
            return CRITERIA_CREATED
          end
      
      else
      #if the trophy/title cup property was found, then add the additional criteria to the trophy/title cup 
        prop = Property.by_key(@currentProperty, nil, nil)           
           if (@validData && validate_number(@amount) && (@duration))
             
             if ( is_criteria_already_there(prop.text_value.to_s, @criteria))
              #criteria already exists, explain to user here
              return CRITERIA_ALREADY_EXISTS
             else
              newTextValue = prop.text_value.to_s + ',' + @criteria           
              prop.text_value = newTextValue
              prop.save
              return CRITERIA_CREATED
             end
          end
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
  
  def formatExistingName() 
    if (@currentExistingName.match(/\s/))
      @name =  @currentExistingName.split(' ').map(&:capitalize).join(' ').delete(' ')
    else
      @name = @currentExistingName
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
      reg = /^[0-9]\d*(\.\d+)?$/
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
        unless(name.blank? | metric.blank? | amount.blank? | duration.blank? | durationValue.blank? )       
          @validData = true 
        end
      elsif(@type =="Title Cup")
        unless (name.blank? | metric.blank? )
          @validData = true
        end   
      end
    
     
   end
 
  #see if this criteria already exists
   def is_criteria_already_there(sourcestring, newstring)
     return sourcestring.include? newstring
   end
end
