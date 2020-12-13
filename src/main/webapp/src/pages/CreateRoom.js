export const CreateRoomComponent = () => `
<div id="form-background-img">
        <section id="room-form-main">
            <div class="form-wrap">
                <h1>Add a new Room</h1>
                <p>Type in your ID and type of game</p>
                <form>
                    <div class="form-group">
                        <!-- User name input -->
                        <label for="hostId">Host ID</label>
                        <input type="text" id="hostId" name="hostId">
                    </div>
                    <div class="form-group">
                        <!-- User Role select -->
                        <label for="gametype">Game Categories</label>
                        <select name="gametype" id="gametype">
                            <option value="Action-Adventure">Action-Adventure</option>
                            <option value="Role-playing">Role-playing</option>
                            <option value="Simulation">Simulation</option>
                            <option value="Puzzle">Puzzle</option>
                        </select>
                    </div>

                    <button id="pirate-submit" class="btn">Submit Pirate</button>
                </form>
            </div>
        </section>
    </div>
`;