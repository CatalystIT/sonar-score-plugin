class Userprofile < ActiveRecord::Base
  attr_accessor :uploadedfile , :current_user_id, :path
  
  # Defines the path to store the image
  def newPath(userprofile)
    File.join(Rails.root, '..','images','profiles','users', image)
  end

  # Initialize the params
  def initialize(user_id, image=nil)
    unless userprofile == nil
      @uploadedfile = userprofile['uploadedfile']
    end
    @user_id = user_id

    @path = newPath(@user_id + ".png")
  end

  # Save the uploaded image to sonar file if a file has been selected.
  def saveImage()
    unless uploadedfile == nil
    # checks if the directory exists if not creates it
    unless File.directory?(File.dirname(@path))
      FileUtils.mkdir_p(File.dirname(@path))
    end
    # writes the file
    File.open(@path, "wb") { |f| f.write(@uploadedfile.read) }
    true
    end
  end

  #Shows default image if no image is set
  def readImage()
    unless File.exists?(@path)
      @path = newPath("default.png")
    end
    open(@path, 'rb').read
  end
end
