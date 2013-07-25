class ImagesController < ApplicationController
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
