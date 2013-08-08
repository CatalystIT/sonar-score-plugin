class UserprofilesController < ScoreController
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end

  #POST/Userprofiles
  #Redirects to the itself (Refreshes Page)
  def create   
     @image = Userprofile.new(params[:current_user_id],params[:userprofile])     
     @image.saveImage() 
     redirect_to :controller => "userprofiles", :action => "index", :userid => params[:current_user_id]     
  end

  #GET/Userprofiles
  #Displays the image uploaded on the page
  def display
    @image = Userprofile.new(params[:current_user_id])
    response.headers["Content-Type"] = 'image/png'
    response.headers["Content-Disposition"] = 'inline'
    render :text => @image.readImage()
  end

end
