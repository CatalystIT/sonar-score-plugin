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

class TeamprofilesController < ScoreController
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end

  #POST/Teamprofiles
  #Redirects to the itself (Refreshes Page)
  def create   
     @image = Teamprofile.new(params[:current_team_id],params[:teamprofile])     
     @image.saveImage() 
     redirect_to :controller => "teamprofiles", :action => "index", :teamid => params[:current_team_id]     
  end

  #GET/Teamprofiles
  #Displays the image uploaded on the page
  def display
    @image = Teamprofile.new(params[:current_team_id])
    response.headers["Content-Type"] = 'image/png'
    response.headers["Content-Disposition"] = 'inline'
    render :text => @image.readImage()
  end

end
