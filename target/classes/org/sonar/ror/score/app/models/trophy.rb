class Trophy < ActiveRecord::Base
  attr_accessor :trophy_name

  def saveTrophy(name)
    Property.set("sonar.score.Trophy", name)
    end

end
