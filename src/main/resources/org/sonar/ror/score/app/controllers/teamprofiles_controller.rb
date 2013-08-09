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
