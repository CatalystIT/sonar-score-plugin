class Trophy < ActiveRecord::Base
  attr_accessor :trophy_name, :metric, :amount, :duration
    
  def saveTrophy()
    Property.set("sonar.score.Trophy", "testing ruby")
    end

end
