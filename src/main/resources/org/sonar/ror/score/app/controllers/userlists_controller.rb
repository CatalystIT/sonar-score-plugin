class UserlistsController < ScoreController
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end

  #POST/Images
  #Redirects to the dashboard
#  def create   
#     @image = Userlist.new()     
#     @image.saveImage() 
#     redirect_to :controller => "userlist", :action => "index"    
#  end

  #GET/Images
  #Displays the image uploaded on the widget
	def display
		@image = Userlist.new(params[:current_user_id])
		response.headers["Content-Type"] = 'image/jpeg'
		response.headers["Content-Disposition"] = 'inline'
		render :text => @image.readImage()
	end

end
