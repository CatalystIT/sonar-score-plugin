class Teamlist < ActiveRecord::Base
  attr_accessor :uploadedfile , :current_team_id, :path
  
  # Defines the path to store the image
	def newPath(image)
		File.join(Rails.root, '..','images','profiles','teams', image)
	end

  # Initialize the params
	def initialize(current_team_id, image=nil)
		unless image == nil
		@uploadedfile = image['uploadedfile']
	end
		@current_team_id = current_team_id

		@path = newPath(@current_team_id + ".png")
	end

  #Shows default image if no image is set
  def readImage()
    unless File.exists?(@path)
      @path = newPath("default.png")
    end
    open(@path, 'rb').read
  end
end
