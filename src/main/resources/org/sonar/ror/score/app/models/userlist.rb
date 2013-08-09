class Userlist < ActiveRecord::Base
  attr_accessor :uploadedfile , :current_user_id, :path
  
  # Defines the path to store the image
	def newPath(image)
		File.join(Rails.root, '..','images','profiles','users', image)
	end

  # Initialize the params
	def initialize(current_user_id, image=nil)
		unless image == nil
		@uploadedfile = image['uploadedfile']
	end
		@current_user_id = current_user_id

		@path = newPath(@current_user_id + ".png")
	end

  #Shows default image if no image is set
  def readImage()
    unless File.exists?(@path)
      @path = newPath("default.png")
    end
    open(@path, 'rb').read
  end
end
