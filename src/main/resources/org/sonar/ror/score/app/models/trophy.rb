class Trophy < ActiveRecord::Base
  attr_accessor :name, :metric, :amount, :duration
    
  def initialize(trophy=nil)
    @name = trophy['name']
    @metric = trophy['metric']
    @amount = trophy['amount']
    @duration = trophy['duration']
  end
  
  def saveTrophy()    
    Property.set("sonar.score.Trophy", @name)
    end

end
