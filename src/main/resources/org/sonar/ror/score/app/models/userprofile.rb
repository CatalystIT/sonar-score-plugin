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

class Userprofile < ActiveRecord::Base
  attr_accessor :uploadedfile , :current_user_id, :path, :userid
  
  # Defines the path to store the image
  def newPath(image)
    File.join(Rails.root, '..','images','profiles','users', image)
  end

  # Initialize the params
  def initialize(current_user_id, image=nil)
    unless image == nil
      @uploadedfile = image['uploadedfile']
      @userid = :userid
    end
    @current_user_id = current_user_id

    @path = newPath(@current_user_id + ".png")
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
