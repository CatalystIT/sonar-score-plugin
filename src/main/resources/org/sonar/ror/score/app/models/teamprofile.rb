class Teamprofile < ActiveRecord::Base
  attr_accessor :uploadedfile , :current_team_id, :path, :teamid
  
  # Defines the path to store the image
  def newPath(image)
    File.join(Rails.root, '..','images','profiles','teams', image)
  end

  # Initialize the params
  def initialize(current_team_id, image=nil)
    unless image == nil
      @uploadedfile = image['uploadedfile']
      @teamid = :teamid
    end
    @current_team_id = current_team_id

    @path = newPath(@current_team_id + ".png")
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
