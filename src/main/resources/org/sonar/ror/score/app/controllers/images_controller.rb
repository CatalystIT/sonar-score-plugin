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

class ImagesController < ScoreController
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end

  #POST/Images
  #Redirects to the dashboard
  def create   
     @image = Image.new(params[:project_id],params[:image])     
     @image.saveImage()    
     redirect_to :controller => "dashboard", :action => "index", :id => params[:project_id]    
  end

  #GET/Images
  #Displays the image uploaded on the widget
  def display
    @image = Image.new(params[:project_id])
    response.headers["Content-Type"] = 'image/jpeg'
    response.headers["Content-Disposition"] = 'inline'
    render :text => @image.readImage()
  end

end
