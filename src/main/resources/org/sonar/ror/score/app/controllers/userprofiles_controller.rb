class UserprofilesController < ApplicationController
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end

  #POST/Images
  #Redirects to the dashboard
  def create   
     @image = Userprofile.new(params[:current_user_id],params[:userprofile])     
     @image.saveImage() 
     redirect_to :controller => "userprofiles", :action => "index"    
  end

  #GET/Images
  #Displays the image uploaded on the widget
  def display
    @image = Userprofile.new(params[:current_user_id])
    response.headers["Content-Type"] = 'image/jpeg'
    response.headers["Content-Disposition"] = 'inline'
    render :text => @image.readImage()
  end

end
